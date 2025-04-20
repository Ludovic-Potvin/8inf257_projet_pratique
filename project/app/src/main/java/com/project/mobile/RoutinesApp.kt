package com.project.mobile;

import dagger.hilt.android.HiltAndroidApp;
import android.app.Application;

@HiltAndroidApp
class StoriesApp :Application() // Cant be refactor to routines without breaking hilt, Should be RoutinesApp