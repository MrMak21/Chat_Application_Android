package gr.makris.chatapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public String id;
    public String firstname;
    public String lastname;
    public String email;

    public User(String id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public User() {
    }

    protected User(Parcel in) {
        id = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        email = in.readString();
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
    }
}
