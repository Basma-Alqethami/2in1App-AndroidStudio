package com.example.a2in1app

   import android.graphics.Color
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView

class guessPhraseRecyclerView(private val phrases: ArrayList<String>): RecyclerView.Adapter<guessPhraseRecyclerView.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val Phrase = phrases[position]

        var Holder = holder.itemView.findViewById<TextView>(R.id.textViewRowItem)

        Holder.text = Phrase
        if (Phrase.contains("Wrong")) {
            Holder.setTextColor(Color.RED)
        } else if (Phrase.contains("Found") || Phrase.contains("No")) {
            Holder.setTextColor(Color.GREEN)
        } else {
            Holder.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount() = phrases.size
}
