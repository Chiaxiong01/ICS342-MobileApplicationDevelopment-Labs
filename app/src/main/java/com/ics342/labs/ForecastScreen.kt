package com.ics342.labs
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ics342.labs.data.DayForecast
import com.ics342.labs.data.ForecastTemp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val dataItems = listOf(
    DayForecast(
        1688430192,
        1689825612000,
        1689886872000,
        ForecastTemp(25.0f, 20.0f, 30.0f),
        1023F,
        100
    ),
    DayForecast(
        1688516592,
        1689825732000,
        1689886992000,
        ForecastTemp(15.0f, 33.0f, 60.0f),
        1202F,
        88
    ),
    DayForecast(
        1688602992,
        1689825852000,
        1689887112000,
        ForecastTemp(20.0f, 21.0f, 60.0f),
        1024F,
        97
    ),
    DayForecast(
        1688689392,
        1689825972000,
        1689887232000,
        ForecastTemp(24.0f, 22.0f, 30.0f),
        1025F,
        98
    ),
    DayForecast(
        1688862192,
        1689826092000,
        1689887352000,
        ForecastTemp(16.0f, 23.0f, 30.0f),
        1123F,
        122
    ),
    DayForecast(
        1688948592,
        1689826212000,
        1689887472000,
        ForecastTemp(19.0f, 24.0f, 30.0f),
        1103F,
        123
    ),
    DayForecast(
        1689034992,
        1689826332000,
        1689887592000,
        ForecastTemp(20.0f, 25.0f, 30.0f),
        1204F,
        111
    ),
    DayForecast(
        1689121392,
        1689826452000,
        1689887712000,
        ForecastTemp(26.0f, 26.0f, 30.0f),
        1353F,
        121
    ),
    DayForecast(
        1689207792,
        1689826572000,
        1689887832000,
        ForecastTemp(23.0f, 27.0f, 30.0f),
        1015F,
        102
    ),
    DayForecast(
        1689294192,
        1689826692000,
        1689887952000,
        ForecastTemp(22.0f, 28.0f, 30.0f),
        1068F,
        101
    ),
    DayForecast(
        1689380592,
        1689826812000,
        1689888072000,
        ForecastTemp(17.0f, 29.0f, 30.0f),
        1489F,
        90
    ),
    DayForecast(
        1689466992,
        1689826932000,
        1689888192000,
        ForecastTemp(14.0f, 15.0f, 30.0f),
        1488F,
        54
    ),
    DayForecast(
        1689553392,
        1689827052000,
        1689888312000,
        ForecastTemp(18.0f, 16.0f, 30.0f),
        1126F,
        45
    ),
    DayForecast(
        1689639792,
        1689827172000,
        1689888432000,
        ForecastTemp(27.0f, 17.0f, 30.0f),
        1025F,
        120
    ),
    DayForecast(
        1689726192,
        1689827292000,
        1689888552000,
        ForecastTemp(28.0f, 18.0f, 30.0f),
        1024F,
        110
    ),
    DayForecast(
        1689812592,
        1689827412000,
        1689888672000,
        ForecastTemp(29.0f, 19.0f, 30.0f),
        1045F,
        99
    ),
)

class ForecastScreen {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun DataItemView(dataItem: DayForecast) {
        //Displays all item in dataItem
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.sunnyweather),
                contentDescription = "Sunny Weather Image",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = formatDate(dataItem.date),
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.size(25.dp))
            Column {
                Text(
                    text = "Temp: ${dataItem.temp.day.toInt()}º",
                )
                Row {
                    Text(
                        text = "High: ${dataItem.temp.max.toInt()}º",
                    )
                    Spacer(modifier = Modifier.size(15.dp))
                    Text(
                        text = "Low: ${dataItem.temp.min.toInt()}º",
                    )
                }
            }
            Spacer(modifier = Modifier.size(20.dp))
            Column {
                Text(
                    text = "Sunrise: ${formatTime(dataItem.sunrise)}",
                )
                Text(
                    text = "Sunset: ${formatTime(dataItem.sunset)}",
                )
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun DataItemList(dataItems: List<DayForecast>) {
        //Navigate ti Forecast Screen and creates list
        Column {
            titleBar("Forecast")
            /* Create the list here. This function will call DataItemView() */
            LazyColumn {
                items(items = dataItems) {
                    DataItemView(dataItem = it)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun formatDate(date: Long): String {
        val instant = Instant.ofEpochSecond(date)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
        val formatter = DateTimeFormatter.ofPattern("MMM dd")
        return dateTime.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun formatTime(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
        val formatter = DateTimeFormatter.ofPattern("HH:mm a")
        return dateTime.format(formatter)
    }

    @Composable
    fun titleBar(title: String) {
        //Template to create title bar
        Column(
            modifier = Modifier
                .background(Color.Blue)
                .fillMaxWidth()
        ) {
            Text(
                text = "$title",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}