import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitappnew.R

class ExerciseAdapter(private val itemList: List<Item>, private val context: Context) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    // Класс ViewHolder для хранения представлений элемента списка
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewExercise: TextView = view.findViewById(R.id.textViewExercise)
        val imageViewExercise: ImageView = view.findViewById(R.id.imageViewExercise)
        val textViewComplexity: TextView = view.findViewById(R.id.textViewComplexity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.textViewExercise.text = item.text
        holder.imageViewExercise.setImageResource(item.imageResId)  // Устанавливаем изображение из ресурсов
        holder.textViewComplexity.text = item.complexity

        // Устанавливаем обработчик нажатия на textViewExercise
        holder.textViewExercise.setOnClickListener {
            // Создаем Intent для открытия ссылки
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = itemList.size
}
