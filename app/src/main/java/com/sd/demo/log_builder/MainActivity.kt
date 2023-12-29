package com.sd.demo.log_builder

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sd.lib.log.builder.fLogBuilder
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btn).setOnClickListener { view ->
            logMsg {
                fLogBuilder("onClick").nextLine()

                    .pair("view", view).nextLine()
                    .pairHash("view hash", view).nextLine()
                    .pairHashFull("view hash full", view).nextLine()
                    .pairStr("view string", view).nextLine()

                    .instance(view).nextLine()
                    .instanceFull(view).nextLine()
                    .instanceStr(view).nextLine()

                    .uuid(UUID.randomUUID().toString())
            }
        }

        logMsg {
            fLogBuilder {
                add("onCreate")
                instance(this@MainActivity)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        logMsg {
            fLogBuilder("onStart").instance(this@MainActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        logMsg {
            fLogBuilder("onResume").instance(this@MainActivity)
        }
    }

    override fun onStop() {
        super.onStop()
        logMsg {
            fLogBuilder {
                add("onStop")
                throwable(RuntimeException("hello throwable"))
                instance(this@MainActivity)
            }
        }
    }
}

inline fun logMsg(block: () -> Any) {
    Log.i("log-builder-demo", block().toString())
}