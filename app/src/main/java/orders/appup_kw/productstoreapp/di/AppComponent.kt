package orders.appup_kw.productstoreapp.di

import dagger.Component
import orders.appup_kw.productstoreapp.base.BaseRepository
import orders.appup_kw.productstoreapp.viewModel.CartViewModel
import orders.appup_kw.productstoreapp.viewModel.FavoritesViewModel
import orders.appup_kw.productstoreapp.viewModel.ProductsViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(baseRepository: BaseRepository)
    fun inject(productsViewModel: ProductsViewModel)
    fun inject(favoritesViewModel: FavoritesViewModel)
    fun inject(cartViewModel: CartViewModel)
}