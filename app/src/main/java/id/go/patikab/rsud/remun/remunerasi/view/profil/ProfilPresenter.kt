package id.go.patikab.rsud.remun.remunerasi.view.profil

class ProfilPresenter(private val mView: ProfilView){

    suspend fun showadapter(){
        mView.show()
    }

}