import React, {useEffect, useState} from 'react';
import {Button, Card, CardText, CardTitle} from "reactstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useAppDispatch, useAppSelector} from "app/config/store";
import {reset} from "app/modules/account/password/password.reducer";
import {getSession} from "app/shared/reducers/authentication";
import {IReadingList} from "app/shared/model/reading-list.model";
import {Link} from "react-router-dom";

interface Props {
  data: IReadingList,
  redirectTo: string,
  deletingUrl: string
}



const FavoritesCard: React.FC<Props> = ({data, deletingUrl}: Props) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(reset());
    dispatch(getSession());
    return () => {
      dispatch(reset());
    };
  }, []);

  const account = useAppSelector(state => state.authentication.account);

  const [favorite, setFavorite] = useState(false);

  function handleFavorite(e: boolean) {
    setFavorite(!e)
  }

  return (
  <Card body className="fav-card">
    <FontAwesomeIcon icon={'heart'}
                     fixedWidth
                     color={ favorite ? '#3A7A10' : '#D3D3D3'}
                     className={'fav-icon-heart'}
                     onClick={() => handleFavorite(favorite)}/>
    <CardTitle tag="h5">
      {data.materialId}
    </CardTitle>
    <CardText>
      {account.email} - {account.firstName} {account.lastName}
    </CardText>

    <CardText>
      <div className={'float-end align-items-lg-end'}>
        {data.created}
      </div>
      <div>
        <Button color="primary" size={'md'} className={'fav-button float-right'} >
          Do something
        </Button>
        <Button
          tag={Link}
          to={deletingUrl}
          color="danger"
          size={'md'}
          data-cy="fav-entityDeleteButton"
          className={'fav-button float-right'} >
          Do something
        </Button>
      </div>
    </CardText>
  </Card>
  );
};

export default FavoritesCard;

