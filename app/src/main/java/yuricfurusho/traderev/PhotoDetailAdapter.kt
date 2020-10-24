package yuricfurusho.traderev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_photo_detail_item.view.*
import yuricfurusho.traderev.photos.UnsplashPhoto

class PhotoDetailAdapter() : RecyclerView.Adapter<PhotoDetailAdapter.PhotosViewHolder>() {

    private var photoList = mutableListOf<UnsplashPhoto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_photo_detail_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val unsplashPhoto = photoList[position]
        Glide.with(holder.photo)
            .load(unsplashPhoto.urls?.full)
            .into(holder.photo)


        //TODO add loading
        holder.username.text = unsplashPhoto.user?.username
        unsplashPhoto.likes?.let {
            holder.likes.text = holder.itemView.context.getString(R.string.likes, it.toString())
        }
        holder.description.text = unsplashPhoto.description
    }

    override fun getItemCount(): Int = photoList.size

    fun setList(photoList: List<UnsplashPhoto>) {
        this.photoList.clear()
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    class PhotosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo: ImageView = view.photo
        val username: TextView = view.username
        val likes: TextView = view.likes
        val description: TextView = view.description
    }
}
