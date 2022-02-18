package com.example.photo_album.album_list.infrastructure

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import coil.annotation.ExperimentalCoilApi
import com.example.photo_album.MainActivity
import com.example.photo_album.StartNavigation
import com.example.photo_album.di.AppModule
import com.example.photo_album.ui.theme.Photo_albumTheme
import com.example.photo_album.utils.infrastructure.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class AlbumListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            Photo_albumTheme {
                StartNavigation()
            }
        }
    }

    @Test
    fun albumListScreen_must_have_card_from_assets() {
        composeRule.onNodeWithText("Paisajes").assertExists()
    }
    @Test
    fun albumListScreen_must_navigate_to_PhotoListScreen_when_click_in_album() {
        composeRule.onNodeWithText("Paisajes").performClick()
        composeRule.onNodeWithTag(testTag = TestTags.photoListScreenImage, useUnmergedTree = true)
            .assertExists()
    }
}