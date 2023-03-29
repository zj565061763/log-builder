package com.sd.demo.log_builder

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sd.lib.logbuilder.FLogBuilder
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logMsg {
            FLogBuilder().add("onCreate").instance(this@MainActivity)
        }
    }

    override fun onClick(view: View) {
        logMsg {
            FLogBuilder().add("onClick").nextLine()
                .pair("view", view).nextLine()
                .pairHash("view hash", view).nextLine()
                .pairStr("view string", view).nextLine()
                .instance(view).nextLine()
                .instanceStr(view)
                .uuid(UUID.randomUUID().toString())
        }
    }

    override fun onResume() {
        super.onResume()
        logMsg {
            FLogBuilder().add("onResume").instance(this@MainActivity)
        }
    }

    override fun onStop() {
        super.onStop()
        logMsg {
            FLogBuilder().add("onStop").instance(this@MainActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logMsg {
            FLogBuilder().add("onDestroy").instance(this@MainActivity)
        }
    }
}

inline fun logMsg(block: () -> Any) {
    Log.i("log-builder-demo", block().toString())
}