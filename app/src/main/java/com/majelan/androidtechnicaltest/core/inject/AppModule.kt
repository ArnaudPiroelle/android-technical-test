package com.majelan.androidtechnicaltest.core.inject

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.majelan.androidtechnicaltest.player")
class PlayerModule

@Module
@ComponentScan("com.majelan.androidtechnicaltest.ui")
class ViewModelModule