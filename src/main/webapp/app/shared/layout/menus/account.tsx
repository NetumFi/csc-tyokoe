import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import {Translate, translate} from 'react-jhipster';
import {NavDropdown} from './menu-components';

const accountMenuItemsAuthenticated = (isAuth) => (
  <>
    {
      !isAuth
      &&
      <MenuItem icon="wrench" to="/account/settings" data-cy="settings">
        <Translate contentKey="global.menu.account.settings">Settings</Translate>
      </MenuItem>
      &&
      <MenuItem icon="lock" to="/account/password" data-cy="passwordItem">
      <Translate contentKey="global.menu.account.password">Password</Translate>
      </MenuItem>
    }

    <MenuItem icon="clock-rotate-left" to="/user-search-settings" data-cy="profiili">
      <Translate contentKey="global.menu.account.profile">User profile</Translate>
    </MenuItem>

    <MenuItem icon="clock-rotate-left" to="/user-search-history" data-cy="searchhistory">
      <Translate contentKey="global.menu.account.searchistory">Search history</Translate>
    </MenuItem>
    <MenuItem icon="sign-out-alt" to="/logout" data-cy="logout">
      <Translate contentKey="global.menu.account.logout">Sign out</Translate>
    </MenuItem>
  </>
);

const accountMenuItems = () => (
  <>
    <MenuItem id="login-item" icon="sign-in-alt" to="/login" data-cy="login">
      <Translate contentKey="global.menu.account.login">Sign in</Translate>
    </MenuItem>
    <MenuItem icon="user-plus" to="/account/register" data-cy="register">
      <Translate contentKey="global.menu.account.register">Register</Translate>
    </MenuItem>
  </>
);


export const AccountMenu = ({ isAuthenticated = false, userFullName = '' }) => {

  const actualFullname = userFullName.indexOf('undefined') === -1 ? userFullName : translate('global.menu.account.notLoggedIn')
  return (
    <NavDropdown name={translate('global.menu.account.main', {username: actualFullname})} id="account-menu" data-cy="accountMenu">
      {isAuthenticated ? accountMenuItemsAuthenticated(isAuthenticated) : accountMenuItems()}
    </NavDropdown>
  );
}
export default AccountMenu;
