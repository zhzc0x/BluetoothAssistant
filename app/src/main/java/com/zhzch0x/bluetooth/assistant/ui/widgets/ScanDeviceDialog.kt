package com.zhzch0x.bluetooth.assistant.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zhzc0x.bluetooth.client.Device
import com.zhzch0x.bluetooth.assistant.R
import com.zhzch0x.bluetooth.assistant.ui.theme.ColorDivider


@Composable
fun ScanDeviceDialog(scanning: MutableState<Boolean>, deviceList: SnapshotStateList<Device>,
                     onStartScan: () -> Unit, onStopScan: () -> Unit, onCancel: () -> Unit,
                     onSelectDevice: (Device) -> Unit) = Dialog({
    scanning.value = false
    onCancel()
}, DialogProperties(dismissOnClickOutside=false)){
    Column(Modifier.fillMaxWidth().aspectRatio(0.8f).background(Color.White, RoundedCornerShape(4.dp))){
        Box(Modifier.fillMaxWidth().padding(start = 24.dp, end=24.dp)){
            Text("扫描可用设备", Modifier.padding(top=14.dp, bottom = 14.dp),
                fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Box(Modifier.align(Alignment.CenterEnd).size(36.dp).clickable {
                scanning.value = !scanning.value
                if(scanning.value){
                    deviceList.clear()
                    onStartScan()
                } else {
                    onStopScan()
                }
            }, contentAlignment = Alignment.Center){
                if(scanning.value){
                    CircularProgressIndicator(Modifier.size(24.dp), strokeWidth = 3.dp)
                } else {
                    Image(painterResource(R.drawable.ic_refresh), "",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary))
                }
            }
        }
        Divider(Modifier.fillMaxWidth().height(0.8.dp), color = ColorDivider)
        var selectedDevice by remember { mutableStateOf<Device?>(null) }
        LazyColumn(Modifier.fillMaxWidth().weight(1f)){
            items(deviceList){ device ->
                Row(Modifier.fillMaxWidth().height(48.dp).clickable {
                    selectedDevice = device
                    onSelectDevice(selectedDevice!!)
                }.padding(start = 16.dp, end=16.dp), verticalAlignment = Alignment.CenterVertically){
                    RadioButton(selectedDevice == device, {
                        selectedDevice = device
                        onSelectDevice(selectedDevice!!)
                    })
                    Text(device.name ?: device.address, fontSize = 18.sp)
                }
            }
        }
        Divider(Modifier.fillMaxWidth().height(0.8.dp), color = ColorDivider)
        Box(Modifier.fillMaxWidth().height(50.dp).clickable {
            scanning.value = false
            onCancel()
        }, contentAlignment = Alignment.Center){
            Text("取消", color=MaterialTheme.colorScheme.primary, fontSize = 16.sp,
                fontWeight = FontWeight.Medium)
        }
    }
    LaunchedEffect(Unit){
        scanning.value = true
        deviceList.clear()
        onStartScan()
    }
}