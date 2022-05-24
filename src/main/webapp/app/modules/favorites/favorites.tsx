import React from 'react';
import FavoritesCardList from "app/shared/layout/favorites/favorites-card-list";
import {Translate} from "react-jhipster";

const Favorites = (props) => {
  return (
    <div className={"fav-level1"}>
      <h1>
        <Translate contentKey="global.menu.favorites">Favorties</Translate>
      </h1>
      <div className='container'>
        <FavoritesCardList history={props.history} location={props.location} match={props.match} />
      </div>

    </div>
  );
};

export default Favorites;
