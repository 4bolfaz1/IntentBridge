package ir.abdev.intentbridge

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle

class BridgeActivity : Activity() {

    data class TargetInfo(
        val packageName: String,
        val receiverClass: String? = null,
        val action: String? = null,
        val extras: Map<String, String> = emptyMap()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val info = parseIntentUri(intent.data) ?: return finish()

        val broadcast = buildBroadcastIntent(info)
        sendBroadcast(broadcast)
        finish()
    }

    private fun parseIntentUri(uri: Uri?): TargetInfo? {
        if (uri?.scheme != "bridge") return null

        // host = target package
        val pkg = uri.host ?: return null

        // pathSegments: ["c", "com.foo.BarReceiver", "a", "com.foo.ACTION_DO"]
        val segs = uri.pathSegments
        var cls: String? = null
        var act: String? = null

        var i = 0
        while (i < segs.size - 1) {
            when (segs[i]) {
                "c" -> cls = segs[i + 1]
                "a" -> act = segs[i + 1]
            }
            i += 2
        }

        // query parameter → extras
        val extras = uri.queryParameterNames.associateWith { uri.getQueryParameter(it)!! }

        return TargetInfo(
            packageName   = pkg,
            receiverClass = cls,
            action        = act,
            extras        = extras
        )
    }

    private fun buildBroadcastIntent(info: TargetInfo): Intent {
        return Intent().apply {
            info.action?.let { action = it }
            if (info.receiverClass != null) {
                component = ComponentName(info.packageName, info.receiverClass)
            } else {
                setPackage(info.packageName)
            }
            info.extras.forEach { (k, v) -> putExtra(k, v) }
        }
    }
}
