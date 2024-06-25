package com.zhzch0x.bluetooth.assistant.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.zhzch0x.bluetooth.assistant.ui.theme.ColorDivider

private val mtuRange = 23..512

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeMtuDialog(onDismiss: () -> Unit, onConfirmMtu: (Int) -> Unit){
    Dialog(onDismiss){
        Column(
            Modifier.fillMaxWidth().aspectRatio(1.4f)
            .background(Color.White, RoundedCornerShape(4.dp))){
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                Text("修改mtu", Modifier.padding(top=14.dp, bottom = 14.dp),
                    fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            HorizontalDivider(Modifier.fillMaxWidth().height(0.8.dp), color = ColorDivider)
            var inputText by remember { mutableStateOf("") }
            var inputMtu by remember { mutableIntStateOf(0) }
            var inputError by remember { mutableStateOf(false) }
            Box(Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center){
                TextField(inputText, { text ->
                    inputText = text
                    inputError = try {
                        inputMtu = inputText.toInt()
                        inputMtu !in mtuRange
                    } catch (_: NumberFormatException){
                        true
                    }
                }, Modifier.padding(24.dp), isError = inputError, label={
                    Text("mtu范围23~512")
                }, keyboardOptions= KeyboardOptions(keyboardType= KeyboardType.Number))
            }
            HorizontalDivider(Modifier.fillMaxWidth().height(0.8.dp), color = ColorDivider)
            Box(Modifier.fillMaxWidth().height(50.dp).clickable {
                if(inputMtu !in mtuRange){
                    onDismiss()
                    return@clickable
                }
                onConfirmMtu(inputMtu)
                onDismiss()
            }, contentAlignment = Alignment.Center){
                Text("确认", color= MaterialTheme.colorScheme.primary, fontSize = 16.sp,
                    fontWeight = FontWeight.Medium)
            }
        }
    }
}