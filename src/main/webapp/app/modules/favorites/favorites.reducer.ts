import {createAsyncThunk, isFulfilled} from "@reduxjs/toolkit";
import {createEntitySlice, EntityState, serializeAxiosError} from "app/shared/reducers/reducer.utils";
import axios from "axios";
import {defaultValue, IReadingList} from "app/shared/model/reading-list.model";

const initialState: EntityState<IReadingList> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const collectionAPI = 'api/search/get-metadatas';
const individualAPI = 'api/search/get-metadata';

export const getEntities = createAsyncThunk(
  'favoriteList/fetch_entity_list',
  async (ids: string[] | number[]) => {
  const requestUrl = `${collectionAPI}/${ids}`;
  return axios.get<any>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'favoriteList/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${individualAPI}/${id}`;
    return axios.get<any>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);


// slice
export const FavoritesListSlice = createEntitySlice({
  name: 'favoriteList',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
  }

});

export const { reset } = FavoritesListSlice.actions

// Reducer
export default FavoritesListSlice.reducer;
