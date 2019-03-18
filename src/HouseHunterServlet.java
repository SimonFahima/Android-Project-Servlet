import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HouseHunterServlet extends javax.servlet.http.HttpServlet {

    private User admin;
    private Users users;
    private Properties properties;

    private void addAdmin(){
        admin = new User("Admin", "1234", "26", "male");
        users.signup(admin);
    }

    private void addAdminProperties(){
        Property property1 = new Property(admin.getUserName(), "1234 main st. Brooklyn, NY",
                "50", "4", "200000");
        Property property2 = new Property(admin.getUserName(), "1743 canal st. Brooklyn, NY",
                "72", "5", "450000");
        Property property3 = new Property(admin.getUserName(), "Yavne st. Bet Shemesh, Israel",
                "94", "7", "700000");
        Property property4 = new Property(admin.getUserName(), "Mevoh Hareches 43, Har Adar, Israel",
                "38", "1", "173000");

        properties.addProperty(property1);
        properties.addProperty(property2);
        properties.addProperty(property3);
        properties.addProperty(property4);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        users = new Users();
        properties = new Properties();
        addAdmin();
        addAdminProperties();
    }

//    public void service(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response){
//        super.service();
//    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("in doPost");
        String action = request.getParameter("action");
        if(isNullorEmpty(action)){
            System.out.println("action was null or empty");
            return;
        }
        System.out.println("action = " + action);
        switch (action){
            case Variables.ADD_USER:
                System.out.println("in PUT_USER");
                addUser(request, response);
                break;
            case Variables.ADD_PROPERTY:
                System.out.println("in ADD_PROPERTY");
                addProperty(request, response);
                break;
//            case Variables.ADD_IMAGE:
//                System.out.println("in ADD_IMAGE");
//                addImageForProperty(request, response);
//                break;
            default:
                System.out.println("unrecognizable request from client");
                response.getWriter().write(Variables.UNRECOGNIZABLE_REQUEST);
                break;
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("in doGet");
        String action = request.getParameter("action");

        switch (action){
            case Variables.CHECK_USER:
                System.out.println("in CHECK_USER");
                hasUser(request,response);
                break;
            case Variables.LOAD_ALL_PROPERTIES:
                System.out.println("in LOAD_ALL_PROPERTIES");
                loadAllProperties(request, response);
                break;
            case "1":
                int num1 = Integer.parseInt(request.getParameter("num1"));
                int num2 = Integer.parseInt(request.getParameter("num2"));

                int result = num1 + num2;
                System.out.println(result);
                response.getWriter().write(result);
                break;
            default:
                System.out.println("unrecognizable request from client");
                response.getWriter().write(Variables.UNRECOGNIZABLE_REQUEST);
                break;


        }
    }

    public void destroy(){
        super.destroy();

    }

    private boolean isNullorEmpty(String... strings){
        for (String string : strings) {
            if (string == null || string.isEmpty()) {
                return true;
            }
        }
        return false;
    }



    /*---------------------------------------------------------------------------------------------------------------------------*/
    /*-------------------------------------------SERVLET METHODS-----------------------------------------------------------------*/
    /*---------------------------------------------------------------------------------------------------------------------------*/


    /*---------------------------USER RELATED METHODS------------------------------*/

    private void addUser(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String age = request.getParameter("age");
        String isMale = request.getParameter("isMale");

        if(isNullorEmpty(username, password, age, isMale)){
            return;
        }
        User user = new User(username, password, age, isMale);
        if(users.signup(user)){
            response.getWriter().write(Variables.SIGN_UP_SUCCESSFUL);
        }else {
            response.getWriter().write(Variables.USERNAME_TAKEN);
        }

    }

    private void hasUser(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String answer = users.login(username, password);
        System.out.println("answer is " + answer);
        switch (answer){
            case Variables.NO_SUCH_USER:
                response.getWriter().write(Variables.NO_SUCH_USER);
                System.out.println("wrote to client " + Variables.NO_SUCH_USER);
                break;
            case Variables.WRONG_PASSWORD:
                response.getWriter().write(Variables.WRONG_PASSWORD);
                System.out.println("wrote to client " + Variables.WRONG_PASSWORD);
                break;
            case Variables.CORRECT_USER:
                response.getWriter().write(Variables.CORRECT_USER);
                System.out.println("wrote to client " + Variables.CORRECT_USER);
                break;
        }
    }


    /*---------------------------PROPERTY RELATED METHODS------------------------------*/


    private void addProperty(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
        System.out.println("started receiving parameters");
        String username = request.getParameter("username");
        String address = request.getParameter("address");
        System.out.println(address);
        String squareMeters = request.getParameter("squaremeters");
        String bedrooms = request.getParameter("bedrooms");
        String price = request.getParameter("price");

        System.out.println("checking null or empty...");
        if(isNullorEmpty(username, address, squareMeters, bedrooms, price)){
            response.getWriter().write(Variables.PROPERTY_MISSING_INFO);
            System.out.println("property is missing info");
        }else{
            System.out.println("property info is good attempting to add to market...");
            Property property = new Property(username, address, squareMeters, bedrooms, price);
            System.out.println("created property object");
            boolean added = properties.addProperty(property);
            if(!added){
                response.getWriter().write(Variables.PROPERTY_EXISTS);
                System.out.println("property already exists");
            }else {
                response.getWriter().write(Variables.PROPERTY_ADDED);
                System.out.println("property added: " + property.toString());
            }
        }
    }

    private void loadAllProperties(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
        System.out.println("starting to load properties...");
        List<Property> propertyList = new ArrayList<>(properties.propertyMap.values());
        int amountOfProperties = propertyList.size();
        response.getWriter().write(Variables.LOAD_ALL_PROPERTIES);
        response.getWriter().write(Variables.HASHTAG);
        response.getWriter().write(String.valueOf(amountOfProperties));
        response.getWriter().write(Variables.HASHTAG);
        System.out.println("wrote to client amount of properties... amount = " + amountOfProperties);

//        for (Property property:properties.propertyMap.values()){
//            Property p1 = property;
//        }

        for (int i = 0; i < propertyList.size(); i++) {
            Property property = propertyList.get(i);

            response.getWriter().write(property.getUser());
            response.getWriter().write(Variables.DELIMETER);

            response.getWriter().write(property.getAddress());
            response.getWriter().write(Variables.DELIMETER);

            response.getWriter().write(property.getSquareMeters());
            response.getWriter().write(Variables.DELIMETER);

            response.getWriter().write(property.getBedrooms());
            response.getWriter().write(Variables.DELIMETER);

            response.getWriter().write(property.getPrice());
            response.getWriter().write(Variables.HASHTAG);

//            response.getWriter().write(property.getImgBytes());
//            response.getWriter().write(Variables.HASHTAG);

            System.out.println("wrote property " + (i+1) + ".");
        }


    }

//    private void addImageForProperty(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
//        System.out.println("starting to read image");
//        String address = request.getParameter("address");
//        String imgBytesString = request.getParameter("image");
//
//        if(isNullorEmpty(address, imgBytesString)){
//            response.getWriter().write(Variables.ADD_IMAGE_FAILED);
//            System.out.println("ADD_IMAGE_FAILED");
//            System.out.println("address = " + address + ", " + "imgBytesString = " + imgBytesString);
//        }else {
//            if(!properties.propertyMap.containsKey(address)){
//                response.getWriter().write(Variables.PROPERTY_DOES_NOT_EXIST);
//                System.out.println("PROPERTY_DOES_NOT_EXIST");
//            }else {
//                Property property = properties.propertyMap.get(address);
//                property.setImgBytes(imgBytesString);
//                response.getWriter().write(Variables.IMAGE_ADDED);
//                System.out.println("image added to " + property);
//            }
//        }




        //InputStream inputStream = null;

//        try {
//            inputStream = request.getInputStream();
//            int byteArrayLength = inputStream.read();
//            if(byteArrayLength == -1){
//                System.out.println("byte array empty");
//                response.getWriter().write(Variables.ADD_IMAGE_FAILED);
//                return;
//            }
//            byte[] imgBytes = new byte[byteArrayLength];
//            int actuallyRead = inputStream.read(imgBytes);
//            if(actuallyRead != byteArrayLength){
//                response.getWriter().write(Variables.ADD_IMAGE_FAILED);
//                return;
//            }
//            byte[] image = imgBytes;
//            if(properties.propertyMap.containsKey(address)){
//                Property property = properties.propertyMap.get(address);
//                property.setImgBytes(image);
//                response.getWriter().write(Variables.IMAGE_ADDED);
//            }else {
//                System.out.println("failed to add image");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
  //  }
}
