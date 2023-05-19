package com.sd.demo.log_builder

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.demo.log_builder.ui.theme.AppTheme
import com.sd.lib.logbuilder.FLogBuilder
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logMsg {
            FLogBuilder().add("onCreate").instance(this@MainActivity)
        }
        val view = findViewById<View>(Window.ID_ANDROID_CONTENT)
        setContent {
            AppTheme {
                Content(
                    onClick = {
                        logMsg {
                            FLogBuilder().add("onClick").nextLine()
                                .pair("view", view).nextLine()
                                .pairHash("view hash", view).nextLine()
                                .pairStr("view string", view).nextLine()
                                .instance(view).nextLine()
                                .instanceStr(view).nextLine()
                                .uuid(UUID.randomUUID().toString())
                        }
                    },
                )
            }
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

@Composable
private fun Content(
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Button(
            onClick = onClick
        ) {
            Text(text = "button")
        }
    }
}

inline fun logMsg(block: () -> Any) {
    Log.i("log-builder-demo", block().toString())
}