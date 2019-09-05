package com.jac.caravela.scenes.main

import com.jac.caravela.scenes.Scene

interface Main {
    interface View: Scene.View {}
    abstract class Presenter: Scene.Presenter() {}
}