package kim.hsl.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 携程中调用挂起函数返回一个 Flow 异步流
        runBlocking {
            // 调用 Flow#collect 函数, 可以获取在异步流中产生的元素
            flowFunction().collect {
                // 每隔 500ms 即可拿到一个 Int 元素
                // 并且该操作是异步操作, 不会阻塞调用线程
                println(it)
            }
        }
    }

    /**
     * 使用 flow 构建器 Flow 异步流
     * 在该异步流中, 异步地产生 Int 元素
     */
    suspend fun flowFunction() = flow<Int> {
        for (i in 0..2) {
            // 挂起函数 挂起 500ms
            // 在协程中, 该挂起操作不会阻塞调用线程, 会继续执行其它代码指令
            // 500ms 恢复执行, 继续执行挂起函数之后的后续代码指令
            delay(500)
            // 每隔 500ms 产生一个元素
            // 通过调用 FlowCollector#emit 生成一个元素
            emit(i)
        }
    }
}

