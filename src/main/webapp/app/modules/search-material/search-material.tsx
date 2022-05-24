import './search-material.scss';

import React, {useEffect, useState} from 'react';
import {getSortState, JhiItemCount, JhiPagination, translate, Translate, ValidatedForm} from 'react-jhipster';
import {
  Row,
  Col,
  Button,
  Input,
  Badge,
  Container
} from 'reactstrap';

import {useAppDispatch, useAppSelector} from 'app/config/store';
import {reset, handleSearch, addFilter, deleteFilter, setSearchTerms, getDefaultSearchParams} from "app/modules/search-material/search-material.reducer";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {getEntities} from "app/entities/education-level-code-set/education-level-code-set.reducer";
import {getEntities as getReadingListEntities} from "app/entities/reading-list/reading-list.reducer";
import {overridePaginationStateWithQueryParams} from "app/shared/util/entity-utils";
import {ITEMS_PER_PAGE, SORT} from "app/shared/util/pagination.constants";
import SearchCard from "app/shared/layout/search-card/search-card";

export const SearchMaterial = (props) => {
  const account = useAppSelector(state => state.authentication.account);
  const searchparams = useAppSelector(state => state.searchMaterial);
  const educationLevelCodeSets = useAppSelector(state => state.educationLevelCodeSet);
  const currentLocale = useAppSelector(state => state.locale.currentLocale);
  const readingListList = useAppSelector(state => state.readingList.entities);
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'relevance'), props.location.search)
  );

  const reloadResults = () => {
    dispatch(handleSearch({
      page: paginationState.activePage - 1 || 0,
      size: paginationState.itemsPerPage,
      sort: `${paginationState.sort},${paginationState.order}`,
    }));
    dispatch(getReadingListEntities({}));
  };

  useEffect(() => {
    reset();
    dispatch(getEntities({page: 0, size: 999, sort: null}));
    dispatch(getDefaultSearchParams());
  }, [props.location.pathname]);


  useEffect(() => {
    if (props.location.search) {
      reloadResults();
    }
  }, [props.location.search, paginationState.activePage, paginationState.order, paginationState.sort]);

  const filterItems = educationLevelCodeSets.entities;
  useEffect(() => {
    if (filterItems && searchparams.defaultEducationalLevel) {
      handleAddFilter(searchparams.defaultEducationalLevel)
    }
  }, [filterItems, searchparams.defaultEducationalLevel]);

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

  const handleAddFilter = (uuid) => {
    const rajaus = filterItems.filter(i => i.codeId === uuid)[0];
    dispatch(addFilter(rajaus));
  };

  const handleDeleteFilter = (filter) => {
    dispatch(deleteFilter(filter));
  }

  const setInputSearchTerms = (value) => {
    dispatch(setSearchTerms(value.target.value));
  }

  const handlePagination = currentPage => {
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

    const endURL = `?page=${currentPage}&sort=relevance,${paginationState.order}`;
    if (searchparams.material.hits && props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };


  const codeLocales = {"fi": "labelFi", "en": "labelEn", "sv": "labelSv"};

  const sort = ["Suosituin ensin"];

  return (
    <Container>
        <h2>
          <Translate contentKey="materialsearch.title.welcome" interpolate={{name: account?.firstName}}>
            Tervetuloa, {account?.firstName}.
          </Translate>
        </h2>
        <Container>
        <ValidatedForm id="materialsearch-form" onSubmit={reloadResults} defaultValues={searchparams}>
          <Row className="mt-3">
            <Col sm="10">
              <Input
                type="text"
                name="searchTerms"
                label={translate('materialsearch.form.searchterms')}
                placeholder={translate('materialsearch.form.searchterms-placeholder')}
                data-cy="searchTerms"
                onChange={setInputSearchTerms}
                defaultValue={searchparams.searchTerms}
              />
            </Col>
            <Col xs="1">
              <Button color="primary" type="submit" data-cy="submit">
                <Translate contentKey="materialsearch.form.button">Search</Translate>
              </Button>
            </Col>
          </Row>
          <Row className="mt-3">
            <Col
              lg="3">
              <Input
                name="filter"
                label={translate('materialsearch.form.filter')}
                id="filter"
                placeholder={translate('materialsearch.form.filter')}
                type="select"
                data-cy="filter"
                onChange={(params) => handleAddFilter(params.target.value)}
                defaultValue={null}>
                {
                  [<option key="firstElement"> {translate('materialsearch.form.filter')} </option>]
                    .concat(filterItems.filter(item => !searchparams.filters.includes(item)).map(filterItem => (
                      <option value={filterItem.codeId} key={filterItem.codeId}>
                        {filterItem[codeLocales[currentLocale]]}
                      </option>
                    )))
                }
              </Input>
            </Col>
            <Col sm="9" className="filterList">
              {searchparams.filters.filter(i => i !== undefined).map(filterItem =>
                <Badge
                  key={filterItem?.codeId}
                  className="filterChip"
                  tabIndex={0}
                  color="primary"
                  onClick={() => handleDeleteFilter(filterItem)}
                  pill>
                  {filterItem["labelFi"]}
                  <span className="icon">
                    <FontAwesomeIcon icon="x"/>
                  </span>
                </Badge>)}
            </Col>
          </Row>
          <Row  className="mt-3">
            <Col lg="3">
              <Input
                type="select">
                {sort.map(s => <option key={s}>{s}</option>)}
              </Input>
            </Col>
          </Row>
        </ValidatedForm>
        </Container>
      <div  className="mt-3" />
      {searchparams.material?.hits && <p>Hakutuloksia {searchparams.material?.hits}</p>}
      <div className="results">
        {searchparams.material?.results.map(result => {
          return (
            <SearchCard key={result.key}
                        result={result}
                        readingListList={readingListList}
                        materialName={m => m.materialname}
                        lang={currentLocale}
                        description={d => d.description} />)
        })}
      </div>
      {searchparams.material?.results && searchparams.material?.results?.length > 0 &&
      <div className={searchparams.material?.results && searchparams.material?.results?.length > 0 ? '' : 'd-none'}>
        <div className="justify-content-center d-flex">
          <JhiItemCount page={paginationState.activePage} total={searchparams.material?.hits} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
        </div>
        <div className="justify-content-center d-flex">
          <JhiPagination
            activePage={paginationState.activePage}
            onSelect={handlePagination}
            maxButtons={5}
            itemsPerPage={paginationState.itemsPerPage}
            totalItems={searchparams.material?.hits}
          />
        </div>
      </div>
      }
    </Container>
  );
};

export default SearchMaterial;
