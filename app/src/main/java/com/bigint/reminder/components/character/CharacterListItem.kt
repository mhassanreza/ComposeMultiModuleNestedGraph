package com.androidfactory.simplerick.components.character

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bigint.reminder.components.character.CharacterStatusCircle
import com.bigint.reminder.components.common.CharacterImage
import com.bigint.reminder.components.common.DataPoint
import com.bigint.reminder.components.common.DataPointComponent
import com.bigint.reminder.ui.theme.RickAction
import com.bigint.network.models.domain.CharacterDto
import com.bigint.network.models.domain.CharacterGender
import com.bigint.network.models.domain.CharacterStatus
import com.bigint.network.models.domain.Location
import com.bigint.network.models.domain.Origin

@Composable
fun CharacterListItem(
    modifier: Modifier = Modifier,
    character: CharacterDto,
    characterDataPoints: List<DataPoint>,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(140.dp)
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.Transparent, RickAction)),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Box {
            CharacterImage(
                imageUrl = character.imageUrl,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )
            CharacterStatusCircle(
                status = character.status,
                modifier = Modifier.padding(start = 6.dp, top = 6.dp)
            )
        }
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            content = {
                items(
                    items = listOf(
                        DataPoint(
                            title = "Name",
                            description = character.name
                        )
                    ) + characterDataPoints,
                    key = { it.hashCode() }
                ) { dataPoint ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        DataPointComponent(dataPoint = sanitizeDataPoint(dataPoint))
                    }
                }
            })
    }
}

private fun sanitizeDataPoint(dataPoint: DataPoint): DataPoint {
    val newDescription = if (dataPoint.description.length > 14) {
        dataPoint.description.take(12) + ".."
    } else {
        dataPoint.description
    }
    return dataPoint.copy(description = newDescription)
}

@Preview
@Composable
private fun CharacterListItemPreview() {
    CharacterListItem(
        character = CharacterDto(
            created = "timestamp",
            episodeIds = listOf(1, 2, 3, 4, 5),
            gender = CharacterGender.Male,
            id = 123,
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            location = Location(
                name = "Earth",
                url = ""
            ),
            name = "Morty Smith",
            origin = Origin(
                name = "Earth",
                url = ""
            ),
            species = "Human",
            status = CharacterStatus.Alive,
            type = ""
        ),
        characterDataPoints = listOf(
            DataPoint(title = "Title 1", description = "Description 1"),
            DataPoint(title = "Title 2", description = "Description 2"),
            DataPoint(title = "Title 3", description = "Description 3"),
            DataPoint(title = "Title 4", description = "Description 4"),
            DataPoint(title = "Title 5", description = "Description 5"),
        ),
        onClick = {}
    )
}