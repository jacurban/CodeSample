package com.jac.caravela.scenes.coordinfo

import com.jac.caravela.scenes.Scene


interface CoordInfo {
    interface View: Scene.View {}
    abstract class Presenter: Scene.Presenter() {}
}