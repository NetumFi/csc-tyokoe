import './search-material.scss';

import React, {useEffect} from 'react';
import {translate, Translate, ValidatedForm} from 'react-jhipster';
import {Badge, Button, Col, Container, Input, Row} from 'reactstrap';

import {useAppDispatch, useAppSelector} from 'app/config/store';
import {
  addFilter,
  deleteFilter,
  handleSearch,
  setSearchTerms
} from "app/modules/search-material/search-material.reducer";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {getEntities} from "app/entities/education-level-code-set/education-level-code-set.reducer";
import SearchCard from "app/shared/layout/search-card/search-card";

export const SearchMaterial = (props) => {
  const account = useAppSelector(state => state.authentication.account);
  const searchparams = useAppSelector(state => state.searchMaterial);
  const educationLevelCodeSets = useAppSelector(state => state.educationLevelCodeSet);
  const currentLocale = useAppSelector(state => state.locale.currentLocale);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntities({page: 0, size: 100, sort: null}));
  }, [props.location]);

  const filterItems = educationLevelCodeSets.entities;

  const handleValidSubmit = (data) => {
    dispatch(handleSearch(data));
  };

  const handleAddFilter = (params) => {
    const rajaus = filterItems.filter(i => i.codeId === params.target.value)[0];
    dispatch(addFilter(rajaus));
  };

  const handleDeleteFilter = (filter) => {
    dispatch(deleteFilter(filter));
  }

  const setInputSearchTerms = (value) => {
    dispatch(setSearchTerms(value.target.value));
  }

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
        <ValidatedForm id="materialsearch-form" onSubmit={handleValidSubmit} defaultValues={searchparams}>
          <Row>
            <Col sm="10">
              <label className={'not-visible-label'}>
                hakusana:
              </label>
              <Input
                type="text"
                name="searchTerms"
                placeholder={translate('materialsearch.form.searchterms-placeholder')}
                data-cy="searchTerms"
                onChange={setInputSearchTerms}
              />
            </Col>
            <Col xs="1">
              <Button color="primary" type="submit" data-cy="submit">
                <Translate contentKey="materialsearch.form.button">Search</Translate>
              </Button>
            </Col>
          </Row>
          <Row className={'cust-select-limit'}>
            <Col lg="3">
              <label className={'not-visible-label'}>
                raja:
              </label>
              <Input
                name="filter"
                label={translate('materialsearch.form.filter')}
                id="filter"
                placeholder={translate('materialsearch.form.filter')}
                type="select"
                data-cy="filter"
                onChange={handleAddFilter}
                defaultValue={null}>
                {
                  [
                    <option key="firstElement"> {
                    translate('materialsearch.form.filter')} </option>
                  ].concat(filterItems.filter(item => !searchparams.filters.includes(item)).map(filterItem => (
                      <option value={filterItem.codeId} key={filterItem.codeId}>
                        {filterItem[codeLocales[currentLocale]]}
                      </option>
                    )))
                }
              </Input>
            </Col>
            <Col sm="9" className="filterList">
              {searchparams.filters.map(filterItem =>
                <Badge
                  key={filterItem.codeId}
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
        </ValidatedForm>
        </Container>

      <div >
        <Container className={'cust-seacrh-component'}>
          <Row>
            <Col>{searchparams.material?.hits &&
              <h3 className={"cust-results"}>
                {searchparams.material?.hits} <Translate contentKey="search.cardseacrhresult"> search results</Translate>
            </h3>}</Col>
            {/* eslint-disable-next-line no-console */}
            {searchparams.material?.hits
              && searchparams.material?.hits >= 2
              && <Col className={'cust-select-input'}>
                <label className={'not-visible-label'}>
                  Sorting:
                </label>
                <Input type="select" name="select" id="selectSorting" data-cy="selectSorting">
                  {sort.map(s => <option key={s}>{s}</option>)}
                </Input>
              </Col>}
          </Row>

        </Container>
        {searchparams.material?.results.map(result => {
          return (
            <SearchCard key={result.key}
                        result={result}
                        materialName={m => m.materialname}
                        lang={currentLocale}
                        description={d => d.description} />)
        })}
      </div>
    </Container>
  );
};

export default SearchMaterial;
