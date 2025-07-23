package ir.abdev.intentbridge

import android.app.Activity
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
//import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var shortcutManager: ShortcutManager
    private lateinit var etId: EditText
    private lateinit var etLabel: EditText
    private lateinit var etUri: EditText
    private lateinit var btnTest: Button
    private lateinit var btnSave: Button
    private lateinit var btnManage: Button
    private lateinit var btnLink: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize ShortcutManager
        shortcutManager = getSystemService(ShortcutManager::class.java)

        // find views
        etId      = findViewById(R.id.etShortcutId)
        etLabel   = findViewById(R.id.etShortcutLabel)
        etUri     = findViewById(R.id.etShortcutUri)
        btnTest   = findViewById(R.id.btnTest)
        btnSave   = findViewById(R.id.btnSave)
        btnManage = findViewById(R.id.btnManage)
        btnLink   = findViewById(R.id.btnLink)

        // test button listener
        btnTest.setOnClickListener {
            val uriString = etUri.text.toString()
            if (uriString.isBlank()) {
                Toast.makeText(this, "URI cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                testShortcut(uriString)
            }
        }

        // save button listener
        btnSave.setOnClickListener {
            val id    = etId.text.toString()
            val label = etLabel.text.toString()
            val uri   = etUri.text.toString()
            if (id.isBlank() || label.isBlank() || uri.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                createOrUpdateShortcut(id, label, uri)
                Toast.makeText(this, "Shortcut saved", Toast.LENGTH_SHORT).show()
            }
        }
        
        btnManage.setOnClickListener {
            startActivity(Intent(this, ShortcutListActivity::class.java))
        }
        
        btnLink.setOnClickListener {
            val uri = Uri.parse("https://github.com/4bolfaz1/IntentBridge")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage(null)
            startActivity(intent)
        }

    }

    private fun createOrUpdateShortcut(id: String, shortLabel: String, uri: String) {
        // build Intent to BridgeActivity with deep link URI
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
            setClassName("ir.abdev.intentbridge", "ir.abdev.intentbridge.BridgeActivity")
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // create dynamic shortcut
        val shortcut = ShortcutInfo.Builder(this, id)
            .setShortLabel(shortLabel)
            .setLongLabel(shortLabel)
            //.setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
            .setIntent(intent)
            .build()

        shortcutManager.addDynamicShortcuts(listOf(shortcut))
    }

    private fun testShortcut(uri: String) {
        val testIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
            setClassName("ir.abdev.intentbridge", "ir.abdev.intentbridge.BridgeActivity")
        }
        startActivity(testIntent)
    }

    private fun removeShortcut(id: String) {
        shortcutManager.removeDynamicShortcuts(listOf(id))
    }
}
