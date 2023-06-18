#!/usr/bin/env groovy
package com.utils


class JenkinsfileUtil {
//    def steps
//    def script
//
//    JenkinsfileUtil(def steps, def script) {
//        this.steps = steps
//        this.script = script
//    }

    // Ejemplo de acceso a variables definidas en el script del pipeline
    static def getProjectName() {
        return "azar"
    }

}