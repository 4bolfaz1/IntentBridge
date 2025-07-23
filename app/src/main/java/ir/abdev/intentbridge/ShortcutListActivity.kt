package ir.abdev.intentbridge

import android.app.Activity
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ShortcutListActivity : Activity() {

    private lateinit var shortcutManager: ShortcutManager
    private lateinit var listView: ListView
    private var shortcuts: MutableList<ShortcutInfo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shortcut_list)

        shortcutManager = getSystemService(ShortcutManager::class.java)
        listView = findViewById(R.id.lvShortcuts)

        loadShortcuts()
    }

    private fun loadShortcuts() {
        // Fetch dynamic shortcuts
        shortcuts.clear()
        shortcuts.addAll(shortcutManager.dynamicShortcuts)
        listView.adapter = ShortcutAdapter()
    }

    inner class ShortcutAdapter : BaseAdapter() {
        override fun getCount(): Int = shortcuts.size
        override fun getItem(position: Int): Any = shortcuts[position]
        override fun getItemId(position: Int): Long = position.toLong()
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: layoutInflater.inflate(
                R.layout.row_shortcut, parent, false
            )

            val info = shortcuts[position]
            view.findViewById<TextView>(R.id.tvId).text = info.id
            view.findViewById<TextView>(R.id.tvLabel).text = info.shortLabel

            val btnDelete = view.findViewById<Button>(R.id.btnDelete)
            btnDelete.setOnClickListener {
                // Remove the shortcut
                shortcutManager.removeDynamicShortcuts(listOf(info.id))
                Toast.makeText(this@ShortcutListActivity,
                    "Removed ${info.id}", Toast.LENGTH_SHORT).show()
                // Refresh list
                loadShortcuts()
            }

            return view
        }
    }
}
