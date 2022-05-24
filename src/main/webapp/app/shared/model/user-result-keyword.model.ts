import { IUser } from 'app/shared/model/user.model';

export interface IUserResultKeyword {
  id?: number;
  resultKeyword?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IUserResultKeyword> = {};
