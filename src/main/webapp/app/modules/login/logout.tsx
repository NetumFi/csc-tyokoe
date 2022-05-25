import React, { useLayoutEffect } from 'react';
import { Translate, translate } from 'react-jhipster';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { logout } from 'app/shared/reducers/authentication';

export const Logout = () => {
  const logoutUrl = useAppSelector(state => state.authentication.logoutUrl);
  const dispatch = useAppDispatch();

  useLayoutEffect(() => {
    dispatch(logout());
    if (logoutUrl) {
      window.location.href = logoutUrl;
    }
  });

  const toHomePage = () => {
    setTimeout(() => {
      window.location.href = "/"
    }, 3000);


  }

  return (
    <div className="p-5">
      <h4><Translate contentKey="global.menu.account.logout">Logged out successfully!</Translate></h4>
      <>{toHomePage()}</>
    </div>
  );
};

export default Logout;
