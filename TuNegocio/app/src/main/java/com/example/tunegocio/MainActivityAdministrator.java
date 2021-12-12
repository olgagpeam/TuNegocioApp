package com.example.tunegocio;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.tunegocio.FragmentAdministrator.CustomerAdministrator;
import com.example.tunegocio.FragmentAdministrator.DetailAdministrator;
import com.example.tunegocio.FragmentAdministrator.EmployeeAdministrador;
import com.example.tunegocio.FragmentAdministrator.ProductAdministrator;
import com.example.tunegocio.FragmentAdministrator.ProfileAdministrator;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityAdministrator extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

  private DrawerLayout drawerLayout;
  private FirebaseAuth auth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_administrator);
    auth = FirebaseAuth.getInstance();
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    drawerLayout = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.navigation_view);
    navigationView.setNavigationItemSelectedListener(this);

    MenuItem menuItem = navigationView.getMenu().getItem(0);
    onNavigationItemSelected(menuItem);
    menuItem.setChecked(true);

    drawerLayout.addDrawerListener(this);

    View header = navigationView.getHeaderView(0);
    header.findViewById(R.id.header_title).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(MainActivityAdministrator.this, getString(R.string.title_click),
                Toast.LENGTH_SHORT).show();
      }
    });
    if(savedInstanceState==null){ //fragment principal
      getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment,
              new EmployeeAdministrador()).commit();
      navigationView.setCheckedItem(R.id.nav_empleados);
    }
  }

  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    int title;

    switch (menuItem.getItemId()) {
      case R.id.nav_inventario:
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment,
                new ProductAdministrator()).commit();
        break;
      case R.id.nav_ventas:
        title = R.string.menu_ventas;
        break;
      case R.id.nav_clientes:
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment,
              new CustomerAdministrator()).commit();
        break;
      case R.id.nav_informes:
        title = R.string.menu_informes;
        break;
      case R.id.nav_proveedores:
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment,
                new DetailAdministrator()).commit();
        break;
      case R.id.nav_empleados:
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment,
                new EmployeeAdministrador()).commit();
        break;
      case R.id.nav_info_negocio:
        title = R.string.menu_info_negocio;
        break;
      case R.id.nav_cuenta:
       getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment,
         new ProfileAdministrator()).commit();
       break;
      case R.id.nav_cerrar:
        CerrarSesion();
        Toast.makeText(MainActivityAdministrator.this,"Cerraste sesión",Toast.LENGTH_SHORT).show();
        break;
      default: //
        throw new IllegalArgumentException("menu option not implemented!!");
    }
    drawerLayout.closeDrawer(GravityCompat.START);

    return true;
  }
  private void CerrarSesion(){
    auth.signOut();
    startActivity(new Intent(MainActivityAdministrator.this, ActivityLogin.class));
    finish();
  }

  @Override
  public void onDrawerSlide(@NonNull View view, float v) {
    //cambio en la posición del drawer
  }

  @Override
  public void onDrawerOpened(@NonNull View view) {
    //el drawer se ha abierto completamente
    Toast.makeText(this, getString(R.string.navigation_drawer_open),
            Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onDrawerClosed(@NonNull View view) {
    //el drawer se ha cerrado completamente
  }

  @Override
  public void onDrawerStateChanged(int i) {
    //cambio de estado, puede ser STATE_IDLE, STATE_DRAGGING or STATE_SETTLING
  }

}
