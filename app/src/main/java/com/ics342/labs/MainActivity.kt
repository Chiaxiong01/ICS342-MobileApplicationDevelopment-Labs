package com.ics342.labs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                val viewModel: WeatherViewModel = hiltViewModel()
                WeatherView(viewModel)
            }
        }
    }

    @Composable
    fun WeatherView(
        viewModel: WeatherViewModel
    ) {
        val weatherData = viewModel.weatherData.observeAsState()
        var showDialog = false

        LaunchedEffect(Unit) {
            viewModel.viewAppeared()
        }

        Column {
            Text("City")
            Row {
                Text(text = "temp")
                    weatherData.value?.let {
                    WeatherConditionIcon(url = it.iconUrl)
                }
            }
        }

        fun onSearch() {
            if (viewModel.isValidZipCode()) {
                viewModel.viewAppeared()
            } else {
                showDialog = true
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Invalid ZIP code") },
                text = { Text("Please enter a valid 5-digit ZIP code.") },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }

        Button(onClick = { onSearch() }) {
            Text("Search")
        }
    }

    @Composable
    fun WeatherConditionIcon(
        url: String
    ) {
        AsyncImage(model = url, contentDescription = "")
    }

    @Composable
    fun WeatherApp(navController: NavController) {
        //Aligns everything under each other
        Column {
            //Create weather app title
            titleBar("My Weather App")

            //Create top section of screen (city,State)
            Text(
                text = stringResource(R.string.stateCity),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            )

            //Create middle section of screen
            //Combine degree, text, & image as one element (row)
            Row {
                //Combine degree & text as one element to the left
                Column {
                    //Create degree
                    Text(
                        text = stringResource(R.string.degree),
                        fontSize = 60.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(200.dp)
                    )

                    //Create text
                    Text(
                        text = stringResource(R.string.text),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(200.dp)
                    )
                }

                //Add sunnyweather image
                Image(
                    painter = painterResource(id = R.drawable.sunnyweather),
                    contentDescription = "Sunny Weather Image",
                    modifier = Modifier.size(100.dp)
                )
            }

            //Create bottom section of screen
            Column(
                modifier = Modifier
                    .padding(35.dp)
                    .fillMaxWidth()
            ) {
                //Create lowDegree
                textTemplate(stringResource(R.string.lowDegree))
                //Create highDegree
                textTemplate(stringResource(R.string.highDegree))
                //Create humidity
                textTemplate(stringResource(R.string.humidity))
                //Create pressure
                textTemplate(stringResource(R.string.pressure))
            }

            //Create Forecast Button
            Column(
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(16.dp)
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable(onClick = {
                        navController.navigate("ForecastListDisplay");
                    })
            ) {
                Text(
                    text = "Forecast",
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            //End
        }
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

    @Composable
    fun textTemplate(text: String) {
        //Template to add text (size:18)
        //Template to add text (size:18)
        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}