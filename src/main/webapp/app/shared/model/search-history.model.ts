import { IUser } from 'app/shared/model/user.model';

export interface ISearchHistory {
  id?: number;
  searchTerm?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<ISearchHistory> = {};
