package ar.edu.unq.desapp.grupoC012022.backenddesappapi.jobs

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.CurrencyService
import org.springframework.beans.factory.annotation.Autowired
import java.io.BufferedReader
import java.io.InputStreamReader


class CurrencyPriceJob {

    @Autowired
    private lateinit var currencyService: CurrencyService

    fun run() {
        Thread {
            while(true){

                println("Updating currencies job")

                val command = arrayOf("curl", "-X", "POST", "http://localhost:8080/currencies", "-H", "accept: */*", "-d", "")

                val builder = ProcessBuilder(*command)
                builder.redirectErrorStream(true)

                var curlResult = ""
                var line: String?

                try {
                    val process = builder.start()
                    val r = BufferedReader(InputStreamReader(process.inputStream))

                    while (true) {
                        line = r.readLine()
                        if (line == null) {
                            break
                        }
                        curlResult += line
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                println("curlResut: $curlResult")

                Thread.sleep(600000)
            }
        }.start()
    }
}