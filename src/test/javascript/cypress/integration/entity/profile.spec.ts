import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('Profile e2e test', () => {
  const profilePageUrl = '/profile';
  const profilePageUrlPattern = new RegExp('/profile(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const profileSample = {};

  let profile: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/profiles+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/profiles').as('postEntityRequest');
    cy.intercept('DELETE', '/api/profiles/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (profile) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/profiles/${profile.id}`,
      }).then(() => {
        profile = undefined;
      });
    }
  });

  it('Profiles menu should load Profiles page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('profile');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Profile').should('exist');
    cy.url().should('match', profilePageUrlPattern);
  });

  describe('Profile page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(profilePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Profile page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/profile/new$'));
        cy.getEntityCreateUpdateHeading('Profile');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', profilePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/profiles',
          body: profileSample,
        }).then(({ body }) => {
          profile = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/profiles+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/profiles?page=0&size=20>; rel="last",<http://localhost/api/profiles?page=0&size=20>; rel="first"',
              },
              body: [profile],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(profilePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Profile page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('profile');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', profilePageUrlPattern);
      });

      it('edit button click should load edit Profile page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Profile');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', profilePageUrlPattern);
      });

      it('last delete button click should delete instance of Profile', () => {
        cy.intercept('GET', '/api/profiles/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('profile').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', profilePageUrlPattern);

        profile = undefined;
      });
    });
  });

  describe('new Profile page', () => {
    beforeEach(() => {
      cy.visit(`${profilePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Profile');
    });

    it('should create an instance of Profile', () => {
      cy.get(`[data-cy="keywords"]`).type('Shoes Islands Global').should('have.value', 'Shoes Islands Global');

      cy.get(`[data-cy="education"]`).type('Hat Designer').should('have.value', 'Hat Designer');

      cy.get(`[data-cy="language"]`).type('compelling').should('have.value', 'compelling');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        profile = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', profilePageUrlPattern);
    });
  });
});
