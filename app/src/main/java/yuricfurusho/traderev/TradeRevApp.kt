package yuricfurusho.traderev

import android.app.Application

class TradeRevApp : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}
