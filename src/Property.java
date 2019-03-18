
public class Property {


    private String user;
    private String address;
    private String price;
    private long timeOfUpload;
    private String bedrooms;
    private String squareMeters;
    private String imgBytes;
    private int flag;


    public Property(String user, String address, String squareMeters, String bedrooms, String price) {
        this.user = user;
        this.address = address;
        this.price = price;
        this.bedrooms = bedrooms;
        this.squareMeters = squareMeters;
//        this.flag  = flag;
//        this.imgBytes = imgBytes;
//        setTimeOfUpload();
    }



    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String setAmountOfBedrooms(int bedrooms) {
        String bedroomString = String.valueOf(bedrooms);
        bedroomString += "br";
        return bedroomString;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public String getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(String squareMeters) {
        this.squareMeters = squareMeters;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setTimeOfUpload(){
        this.timeOfUpload = System.currentTimeMillis();
    }

    public String  getImgBytes() {
        return imgBytes;
    }

    public void setImgBytes(String imgBytes) {
        this.imgBytes = imgBytes;
    }

    @Override
    public String toString() {
        return this.user + Variables.DELIMETER + this.address + Variables.DELIMETER +
                this.bedrooms + Variables.DELIMETER + this.squareMeters +
                Variables.DELIMETER + this.price + Variables.DELIMETER + this.imgBytes;
    }
}
