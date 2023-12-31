package com.example.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodel.DataSource.jenis
import com.example.viewmodel.DataSource.status
import com.example.viewmodel.ui.theme.ViewModelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    TampilLayout()
                }
            }
        }
    }
}
//Divider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilForm(cobaViewModel: CobaViewModel = viewModel()){

    var textNama by remember {
        mutableStateOf("")
    }
    var textTlp by remember {
        mutableStateOf("")
    }
    var textAmt by remember {
        mutableStateOf("")
    }
    var textemail by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val dataForm: DataForm
    val uiState by cobaViewModel.uiState.collectAsState()
    dataForm = uiState;

    Box(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)

    ){
        Row{
            Image(painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier)
            Spacer(modifier = Modifier.padding(start = 50.dp))
            Text(text = "Register",
                )
        }
    }

    OutlinedTextField(
        value = textNama,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = "Nama Lengkap")},
        onValueChange = {
            textNama = it
        }
    )
    OutlinedTextField(
        value = textTlp,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = "No Telepon")},
        onValueChange = {
            textTlp = it
        }
    )
    OutlinedTextField(
        value = textemail,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = "e - mail")},
        onValueChange = {
            textemail = it
        }
    )

    SelectJK(
        options = jenis.map { id -> context.resources.getString(id)},
        onSelectionChange = {cobaViewModel.setJenisK(it)}
    )
    SelectM(
        options = status.map { id -> context.resources.getString(id)},
        onSelectionChange = {cobaViewModel.Menikah(it)}
    )
    OutlinedTextField(
        value = textAmt,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = "Alamat")},
        onValueChange = {
            textAmt = it
        }
    )
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            cobaViewModel.insertData(textNama, textTlp, dataForm.sex, textAmt
                , dataForm.status, textemail)
        }
    )

    {
        Text(
            text = stringResource(R.string.submit),
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    TextHasil(jenisnya = cobaViewModel.jenisKL, statusnya = cobaViewModel.statusM, alamatnya =cobaViewModel.alamat , emailnya = cobaViewModel.email )
    //TextHasil(jenisnya = cobaViewModel.jenisKL, statusnya = cobaViewModel.statusM, alamatnya = cobaViewModel.alamat, emailnya = cobaViewModel.email)
}

@Composable
fun SelectM(options: List<String>,
            onSelectionChange: (String) -> Unit = {}
){
    var selectedValue by rememberSaveable{mutableStateOf("")}
    Row ( modifier = Modifier.padding(10.dp)) {
        options.forEach{ item ->
            Row (
                modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChange(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChange(item)
                    }
                )
                Text(item)
            }
        }
    }
}
@Composable
fun SelectJK(options: List<String>,
             onSelectionChange: (String) -> Unit = {}
){
    var selectedValue by rememberSaveable{mutableStateOf("")}
    Row ( modifier = Modifier.padding(10.dp)) {
        options.forEach{ item ->
            Row (
                modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChange(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChange(item)
                    }
                )
                Text(item)
            }
        }
    }
}
@Composable
fun TextHasil(jenisnya:String, statusnya: String,  alamatnya: String, emailnya: String){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ){
        Text(text = "Jenis Kelamin :  " + jenisnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )
        Text(text = "Status " + statusnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(text = "Alamatnya : " + alamatnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(text = "Email : " + emailnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }

}


@Composable
fun TampilLayout(modifier: Modifier = Modifier){
    Card(
       modifier = Modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(20.dp)
        ){
            TampilForm()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ViewModelTheme {
        TampilLayout()
    }
}