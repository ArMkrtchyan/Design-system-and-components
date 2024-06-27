package am.acba.component.phoneNumberInput

import android.os.Parcel
import android.os.Parcelable

data class CountryModel(
    val englishName: String? = "",
    val flagResId: Int = 0,
    val name: String? = "",
    val nameCode: String? = "",
    val phoneCode: String? = ""
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(englishName)
        parcel.writeInt(flagResId)
        parcel.writeString(name)
        parcel.writeString(nameCode)
        parcel.writeString(phoneCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CountryModel> {
        override fun createFromParcel(parcel: Parcel): CountryModel {
            return CountryModel(parcel)
        }

        override fun newArray(size: Int): Array<CountryModel?> {
            return arrayOfNulls(size)
        }
    }
}



