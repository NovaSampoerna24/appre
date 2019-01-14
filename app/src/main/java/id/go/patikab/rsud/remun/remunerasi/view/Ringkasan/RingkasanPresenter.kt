package id.go.patikab.rsud.remun.remunerasi.view.Ringkasan


class RingkasanPresenter(private val mView: Ringkasan){

    suspend fun showadapter(){
        mView.show()
    }

}