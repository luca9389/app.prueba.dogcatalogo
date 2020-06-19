package app.prueba.dogcatalogo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DogsResponse(@SerializedName("status") var status: String, @SerializedName("message") var images: List<String>) {

}