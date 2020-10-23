package yuricfurusho.traderev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_photos_item.view.*

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotosViewHolder>() {

    private var photoList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_photos_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        Glide.with(holder.photo)
            .load(photoList[position])
            .into(holder.photo)
    }

    override fun getItemCount(): Int = photoList.size

    fun setList(photoList: List<String>) {
        this.photoList.clear()
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    class PhotosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo: ImageView = view.photo
    }
}
