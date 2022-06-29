package com.example.greenlight.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenlight.callbacks.CountryClickListener
import com.example.greenlight.database.AreaResponse
import com.example.greenlight.databinding.ItemSelectAreaBinding
import com.example.greenlight.models.SalesArea
import com.example.greenlight.models.SalesCountry
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class AreaAdapter @Inject constructor(
    var list: List<AreaResponse>,
    private val cellClickListener: CountryClickListener
) : RecyclerView.Adapter<AppViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = ItemSelectAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val search = list[position]
        holder.setData(search)
        cellClickListener.onStrValue(search.name)
        holder.itemView.setOnClickListener{
            cellClickListener.clickOnArea(search)
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }
    fun upDateList(list: List<AreaResponse>){
        this.list=list
        notifyDataSetChanged()

    }


}

class AppViewHolder(
    val itemlayoutBinding: ItemSelectAreaBinding,
//    val onItemClicked: OnItemClicked
) : RecyclerView.ViewHolder(itemlayoutBinding.root) {

    fun setData(salesCountry: AreaResponse) {
        itemlayoutBinding.apply {
           itemlayoutBinding.tvArea.text=salesCountry.value
        }
    }

}
