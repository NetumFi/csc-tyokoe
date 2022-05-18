import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/profile">
        <Translate contentKey="global.menu.entities.profile" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/search-history">
        <Translate contentKey="global.menu.entities.searchHistory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/age">
        <Translate contentKey="global.menu.entities.age" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/search-setting">
        <Translate contentKey="global.menu.entities.searchSetting" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/reading-list">
        <Translate contentKey="global.menu.entities.readingList" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/note">
        <Translate contentKey="global.menu.entities.note" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/age-code-set">
        <Translate contentKey="global.menu.entities.ageCodeSet" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/education-level-code-set">
        <Translate contentKey="global.menu.entities.educationLevelCodeSet" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
