package com.jac.caravela.scenes.cadastromenu

import com.jac.caravela.scenes.Scene


interface CadastroMenu {
    interface View: Scene.View {}
    abstract class Presenter: Scene.Presenter() {}
}