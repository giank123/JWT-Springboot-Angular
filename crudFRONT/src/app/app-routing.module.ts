import { RegistroComponent } from './auth/registro.component';
import { LoginComponent } from './auth/login.component';
import { IndexComponent } from './index/index.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListaProductoComponent } from './producto/lista-producto.component';
import { DetalleProductoComponent } from './producto/detalle-producto.component';
import { NuevoProductoComponent } from './producto/nuevo-producto.component';
import { EditarProductoComponent } from './producto/editar-producto.component';


const routes: Routes = [
  {path: '', component: IndexComponent},
  {path: 'login', component: LoginComponent},
  {path: 'registro', component: RegistroComponent},
  {path: 'lista', component: ListaProductoComponent},
  {path: 'detalle/:id', component: DetalleProductoComponent},
  {path: 'nuevo', component: NuevoProductoComponent},
  {path: 'editar/:id', component: EditarProductoComponent},
  {path: '**', redirectTo: '', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
