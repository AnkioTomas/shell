package net.ankio.shell.demo

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import net.ankio.shell.Shell

/** 演示：申请 Root / Shizuku 权限后执行 `id` 命令 */
class MainActivity : AppCompatActivity() {

    private val shell by lazy { Shell(packageName) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val output = TextView(this)
        val run = Button(this).apply {
            text = "exec id"
            setOnClickListener {
                lifecycleScope.launch {
                    output.text = runCatching {
                        if (!shell.checkPermission()) shell.requestPermission()
                        shell.exec("id")
                    }.getOrElse { it.toString() }
                }
            }
        }
        setContentView(LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 48, 48, 48)
            addView(run)
            addView(output)
        })
    }

    override fun onDestroy() {
        shell.close()
        super.onDestroy()
    }
}
