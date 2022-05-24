import React, {useEffect, useState} from 'react';
import {RouteComponentProps} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "app/config/store";
import {overridePaginationStateWithQueryParams} from "app/shared/util/entity-utils";
import {getSortState, JhiItemCount, JhiPagination, Translate} from "react-jhipster";
import {ASC, DESC, ITEMS_PER_PAGE, SORT} from "app/shared/util/pagination.constants";
import {getEntities} from "app/entities/reading-list/reading-list.reducer";
import {getEntities as getFavoritesEntities} from "app/modules/favorites/favorites.reducer"
import {Button} from "reactstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import SearchCard from "app/shared/layout/search-card/search-card";

const FavoritesCardList = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const readingListList = useAppSelector(state => state.readingList.entities);
  const loading = useAppSelector(state => state.readingList.loading);
  const totalItems = useAppSelector(state => state.readingList.totalItems);
  const favoriteListResult = useAppSelector(state => state.FavoritesList)
  const currentLocale = useAppSelector(state => state.locale.currentLocale);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }))
      .then((x: any) => x.payload.data.map(y => y.materialId))
      .then(z => dispatch(getFavoritesEntities(z)))


  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const {match} = props;

  return (
    <div>
      <div className="d-flex justify-content-end">
        <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
          <FontAwesomeIcon icon="sync" spin={loading}/>{' '}
          <Translate contentKey="csc2022App.readingList.home.refreshListLabel">Refresh List</Translate>
        </Button>
      </div>
      <div>

        {
          <div className="results">
            {favoriteListResult && favoriteListResult?.entities.length > 0 && favoriteListResult?.entities.map(result => {
              return (
                <SearchCard key={result.id}
                            result={result}
                            readingListList={readingListList}
                            component={'favorites'}
                            handleSyncList={handleSyncList}
                            lang={currentLocale}/>)
            })}
          </div>}

      </div>
      {totalItems ? (
        <div className={readingListList && readingListList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems}
                          itemsPerPage={paginationState.itemsPerPage} i18nEnabled/>
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};
export default FavoritesCardList;
