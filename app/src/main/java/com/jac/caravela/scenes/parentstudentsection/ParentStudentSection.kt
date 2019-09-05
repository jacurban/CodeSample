package com.jac.caravela.scenes.parentstudentsection

import com.jac.caravela.scenes.Scene

interface ParentStudentSection {
    interface View : Scene.View {}
    abstract class Presenter : Scene.Presenter() {}
}