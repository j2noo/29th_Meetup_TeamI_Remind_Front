package com.example.remind.feature.screens.patience

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.remind.R
import com.example.remind.core.common.component.BasicButton
import com.example.remind.core.common.component.BasicTextButton
import com.example.remind.core.designsystem.theme.RemindTheme
import com.example.remind.data.model.graphScoreModel
import com.example.remind.data.repository.CalendarDataSource
import com.jaikeerthick.composable_graphs.composables.line.LineGraph
import com.jaikeerthick.composable_graphs.composables.line.model.LineData
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphColors
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphFillType
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphVisibility
import com.jaikeerthick.composable_graphs.style.LabelPosition
import java.time.LocalDate

@Composable
fun MoodChartScreen() {
    val scrollState = rememberScrollState()
    val graphYaxisList = listOf(
        graphScoreModel(100, R.drawable.ic_verygood),
        graphScoreModel(75, R.drawable.ic_good),
        graphScoreModel(50, R.drawable.ic_normal),
        graphScoreModel(25, R.drawable.ic_bad),
        graphScoreModel(0, R.drawable.ic_terrible),
    )
    RemindTheme {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(19.6.dp))
            Text(
                text = stringResource(id = R.string.무드_차트),
                style = RemindTheme.typography.h2Bold.copy(Color(0xFF303030))
            )
            Spacer(modifier = Modifier.height(22.dp))
            BasicTextButton(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = RemindTheme.colors.slate_600,
                text = "17일째 연속으로 기록 중이에요! 파이팅:)",
                textColor = RemindTheme.colors.slate_100,
                onClick = {  },
                verticalPadding = 7.dp,
                enable = false
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.주간_차트_기록),
                style = RemindTheme.typography.b1Bold.copy(color = RemindTheme.colors.slate_800,)
            )
            Spacer(modifier = Modifier.height(8.dp))
            BasicButton(
                modifier = Modifier.fillMaxWidth(),
                text = "${showSelectDate()} 기록 확인",
                RoundedCorner = 12.dp,
                backgroundColor = RemindTheme.colors.main_6,
                textColor = RemindTheme.colors.white,
                verticalPadding = 18.dp,
                onClick = {  },
                textStyle = RemindTheme.typography.b3Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(12.dp),
                        color = RemindTheme.colors.grayscale_2
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(start = 7.dp, end = 10.dp)
                ) {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for(i in 0..4) {
                            ScoreList(data = graphYaxisList.get(i))
                        }
                    }
                    Spacer(modifier = Modifier.width(25.dp))
                    GraphComponent()
                }
            }
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = stringResource(id = R.string.기분별_활동_차트),
                style = RemindTheme.typography.b1Bold.copy(color = RemindTheme.colors.text)
            )
            Text(
                modifier = Modifier.padding(top = 3.dp),
                text = stringResource(id = R.string.무엇을_할_때_기분이_좋은지_확인),
                style = RemindTheme.typography.b2Medium.copy(color = RemindTheme.colors.grayscale_3)
            )
        }
    }
}


//api연결후 수정 필요함
@Composable
fun GraphComponent(
    modifier: Modifier = Modifier
){
    val xAxisData = listOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat")
    val yAxisData = listOf(100, 75, 100, 25, 75, 50, 50)
    val dataModel = mutableListOf<LineData>()
    for(i in xAxisData.indices) {
        val lineData = LineData(xAxisData[i], yAxisData[i])
        dataModel.add(lineData)
    }
    Column(
        modifier = modifier.padding(top = 20.dp, bottom = 20.dp)
    ) {
        val style = LineGraphStyle(
            visibility = LineGraphVisibility(
                isXAxisLabelVisible = true,
                isYAxisLabelVisible = false,
                isCrossHairVisible = false
            ),
            colors = LineGraphColors(
                lineColor = RemindTheme.colors.main_7,
                pointColor = RemindTheme.colors.main_7,
                clickHighlightColor = RemindTheme.colors.main_6,
                fillType = LineGraphFillType.Gradient(brush = Brush.verticalGradient(listOf(
                    RemindTheme.colors.main_6.copy(alpha = 0.63f), RemindTheme.colors.white
                )))
            ),
            yAxisLabelPosition = LabelPosition.LEFT
        )
        LineGraph(
            data = dataModel,
            style = style,
            onPointClick = {}
        )
    }

}

@Composable
fun ScoreList(
    modifier: Modifier = Modifier,
    data: graphScoreModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier.size(15.dp, 15.dp),
            painter = painterResource(id = data.img),
            contentDescription = null
        )
        Spacer(modifier = modifier.height(1.dp))
        Text(
            text = data.score.toString(),
            style = RemindTheme.typography.c2Medium.copy(color = RemindTheme.colors.text)
        )
    }
}

fun showSelectDate(): String {
    val dataSource = CalendarDataSource()
    val result = dataSource.getDayForSearch(LocalDate.now())
    return result
}


@Preview
@Composable
fun ChartPreview() {

}
