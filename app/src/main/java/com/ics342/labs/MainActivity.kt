package com.ics342.labs

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ics342.labs.ui.theme.LabsTheme
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@JsonClass(generateAdapter = true)
data class DataList(
    val id: Int,
    @Json(name = "give_name") val giveName: String,
    @Json(name = "family_name") val familyName: String,
    val age: Int,
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonData = loadData(resources)
        val data = dataFromJsonString(jsonData)
        setContent {
            LabsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    /*
                    Display the items from the Json file in a LazyColumn
                     */

                    val data = dataFromJsonString(jsonData)
                    DataView(data)
                }
            }
        }
    }
}

private fun loadData(resources: Resources): String {
    return resources
        .openRawResource(R.raw.data)
        .bufferedReader()
        .use { it.readText() }
}

@OptIn(ExperimentalStdlibApi::class)
private fun dataFromJsonString(json: String): List<Data> {
    val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonAdapter: JsonAdapter<List<Data>> = moshi.adapter()
    return jsonAdapter.fromJson(json) ?: listOf()
}

@Composable
fun DataView(dataItemList: List<Data>) {
    LazyColumn {
        items(dataItemList) { item ->
            Text(text = item.give_name)
            Text(text = item.family_name)
            Text(text = item.age.toString())
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}
