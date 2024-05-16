package com.example.remind.feature.screens.patience

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.remind.R
import com.example.remind.core.common.component.BasicButton
import com.example.remind.core.common.component.BasicListItem
import com.example.remind.core.designsystem.theme.RemindTheme
import com.example.remind.data.model.CalendarUiModel
import com.example.remind.data.repository.CalendarDataSource
import com.example.remind.feature.viewmodel.CustomViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(){
    val dataSource = CalendarDataSource()
    var calendarUiModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }
    val scrollState = rememberScrollState()
    RemindTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = RemindTheme.colors.white)
        ) {
            HomeTopBar(onClick = {})
            DateSelectHeader(previousBtn = {}, nextBtn = {}, calendarBtn = {})
            Content(
                modifier = Modifier.fillMaxWidth(),
                data = calendarUiModel,
                onDateClicked = { date ->
                    calendarUiModel = calendarUiModel.copy(
                        selectedDate = date,
                        visibleDates = calendarUiModel.visibleDates.map {
                            it.copy(
                                isSelected = it.date.isEqual(date.date)
                            )
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        ),
                        elevation = 2.dp,
                        ambientColor = Color(0xFF042340).copy(alpha = 0.4f),
                    )
                    .background(
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        ),
                        color = RemindTheme.colors.white,
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.Start
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.약_복용_체크),
                        style = RemindTheme.typography.b2Bold.copy(color = Color(0xFF1F2937))
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    EmptyMedicineList()
                    Spacer(modifier = Modifier.height(23.dp))
                    Text(
                        text = stringResource(id = R.string.오늘_하루_기분이_어떠셨나요),
                        style = RemindTheme.typography.b2Bold.copy(color = Color(0xFF1F2937))
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(id = R.string.당신의_하루가_궁금해요),
                        style = RemindTheme.typography.b3Medium.copy(color = Color(0xFF9B9B9B))
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    EmptyTodayMoodContainer(clickToWrite = {})
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = RemindTheme.colors.white)
            .padding(start = 20.dp, end = 20.dp, top = 23.6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = modifier
                .size(
                    width = 37.dp,
                    height = 22.dp
                )
        )
        Spacer(Modifier.weight(1f))
        IconButton(
            onClick = onClick,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_sos),
                contentDescription = null,
                modifier = modifier
                    .size(width = 24.dp, height = 32.dp),
                tint = RemindTheme.colors.sub_3
            )
        }
    }
}

@Composable
fun DateSelectHeader(
    modifier: Modifier = Modifier,
    previousBtn: () -> Unit,
    nextBtn: () -> Unit,
    calendarBtn: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = RemindTheme.colors.white)
            .padding(start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "2024년 4월 1주차",
            style = RemindTheme.typography.c1Medium.copy(color = RemindTheme.colors.grayscale_3)
        )
        Spacer(modifier = modifier.weight(1f))
        IconButton(
            onClick = nextBtn,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar_previous),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 17.dp, height = 17.dp),
                tint = RemindTheme.colors.slate_400
            )
        }
        IconButton(
            onClick = calendarBtn,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar_next),
                contentDescription = null,
                modifier = modifier
                    .size(width = 17.dp, height = 17.dp),
                tint = RemindTheme.colors.slate_400
            )
        }
        IconButton(
            onClick = calendarBtn,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null,
                modifier = modifier
                    .size(width = 14.dp, height = 14.dp),
                tint = RemindTheme.colors.slate_500
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(
    modifier: Modifier = Modifier,
    data: CalendarUiModel,
    onDateClicked: (CalendarUiModel.Date) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(items = data.visibleDates) { date ->
            DayItem(
                date = date,
                onDateClicked = onDateClicked
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayItem(
    modifier: Modifier = Modifier,
    date: CalendarUiModel.Date,
    onDateClicked: (CalendarUiModel.Date) -> Unit
) {
    Card(
        modifier = modifier
            .clickable {
                onDateClicked(date)
            },
        colors = CardDefaults.cardColors(
            containerColor = if (date.isSelected) RemindTheme.colors.main_6 else RemindTheme.colors.white
        )
    ) {
        Column(

        ) {
            Text(
                text = date.day,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 6.dp, bottom = 7.dp),
                style = RemindTheme.typography.b2Medium.copy(
                    color = if(date.isSelected) RemindTheme.colors.white else RemindTheme.colors.slate_800
                )
            )
            Text(
                text = date.date.dayOfMonth.toString(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 1.dp, horizontal = 7.dp)
                    .background(color = RemindTheme.colors.white, shape = CircleShape),
                style = RemindTheme.typography.b2Medium.copy(color = RemindTheme.colors.slate_800)
            )
            Spacer(modifier = modifier.height(3.dp))
        }
    }
}
@Composable
fun EmptyMedicineList(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = RemindTheme.colors.grayscale_1,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Text(
            modifier = modifier
                .align(Alignment.Center)
                .padding(vertical = 30.dp),
            text = stringResource(id = R.string.등록된_약이_없어요),
            style = RemindTheme.typography.c1Medium.copy(color = RemindTheme.colors.slate_400)
        )
    }
}

@Composable
fun EmptyTodayMoodContainer(
    modifier: Modifier = Modifier,
    clickToWrite: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = RemindTheme.colors.grayscale_1,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Image(
            modifier = modifier
                .padding(
                    start = 50.dp,
                    end = 50.dp,
                    top = 21.dp,
                    bottom = 70.dp
                )
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.img_emptymood),
            contentDescription = null
        )
        BasicButton(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 53.dp, end = 54.dp, bottom = 31.dp),
            text = stringResource(id = R.string.오늘의_기분_기록하기),
            RoundedCorner = 29.dp,
            backgroundColor = RemindTheme.colors.main_6,
            textColor =  RemindTheme.colors.white,
            verticalPadding = 10.dp,
            onClick = clickToWrite,
            textStyle = RemindTheme.typography.b2Bold
        )
    }
}
//다이얼로그
@Composable
fun DialogContent() {
    Column {
        Spacer(
            modifier = Modifier
                .height(9.dp)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.약_미복용_사유),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(top = 9.dp),
            style = RemindTheme.typography.b1Bold.copy(color = RemindTheme.colors.text)
        )
        Spacer(
            modifier = Modifier
                .height(29.dp)
                .fillMaxWidth()
        )

    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomePreview() {
    EmptyTodayMoodContainer(clickToWrite = {})
}

