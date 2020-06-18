package gr.makris.chatapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public String id;
    public String firstname;
    public String lastname;
    public String email;
    public String image;
    public String imageThumb;


    public User(String id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public User(String id, String firstname, String lastname, String email, String image, String imageThumb) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.image = image;
        this.imageThumb = imageThumb;
    }

    public User() {
    }

    protected User(Parcel in) {
        id = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        email = in.readString();
        image = in.readString();
        imageThumb = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(email);
        dest.writeString(image);
        dest.writeString(imageThumb);
    }
}
